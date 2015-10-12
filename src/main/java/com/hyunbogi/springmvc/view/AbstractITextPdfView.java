package com.hyunbogi.springmvc.view;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.Map;

public abstract class AbstractITextPdfView extends AbstractView {
    public AbstractITextPdfView() {
        setContentType("application/pdf");
    }

    @Override
    protected boolean generatesDownloadContent() {
        return true;
    }

    @Override
    protected final void renderMergedOutputModel(Map<String, Object> model,
                                                 HttpServletRequest request,
                                                 HttpServletResponse response) throws Exception {
        // IE workaround: write into byte array first.
        ByteArrayOutputStream baos = createTemporaryOutputStream();

        // Apply preferences and build metadata.
        Document document = new Document(PageSize.A4);
        PdfWriter writer = PdfWriter.getInstance(document, baos);
        prepareWriter(model, writer, request);
        buildPdfMetaData(model, document, request);

        writer.setInitialLeading(16);
        document.open();
        buildPdfDocument(model, document, writer, request, response);
        document.close();

        writeToResponse(response, baos);
    }

    protected void prepareWriter(Map<String, Object> model, PdfWriter writer, HttpServletRequest request) {
        writer.setViewerPreferences(getViewerPreferences());
    }

    protected int getViewerPreferences() {
        return PdfWriter.ALLOW_PRINTING | PdfWriter.PageLayoutSinglePage;
    }

    /**
     * Populate the iText Document's meta fields (author, title, etc.).
     * <br>Default is an empty implementation. Subclasses may override this method
     * to add meta fields such as title, subject, author, creator, keywords, etc.
     * This method is called after assigning a PdfWriter to the Document and
     * before calling {@code document.open()}.
     *
     * @param model    the model, in case meta information must be populated from it
     * @param document the iText document being populated
     * @param request  in case we need locale etc. Shouldn't look at attributes.
     */
    protected void buildPdfMetaData(Map<String, Object> model, Document document, HttpServletRequest request) {
        // empty
    }

    /**
     * Subclasses must implement this method to build an iText PDF document,
     * given the model. Called between {@code Document.open()} and
     * {@code Document.close()} calls.
     * <p>Note that the passed-in HTTP response is just supposed to be used
     * for setting cookies or other HTTP headers. The built PDF document itself
     * will automatically get written to the response after this method returns.
     *
     * @param model    the model Map
     * @param document the iText Document to add elements to
     * @param writer   the PdfWriter to use
     * @param request  in case we need locale etc. Shouldn't look at attributes.
     * @param response in case we need to set cookies. Shouldn't write to it.
     * @throws Exception any exception that occurred during document building
     */
    protected abstract void buildPdfDocument(Map<String, Object> model,
                                             Document document,
                                             PdfWriter writer,
                                             HttpServletRequest request,
                                             HttpServletResponse response) throws Exception;
}
