package com.hyunbogi.springmvc.view;

import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class HelloPdfView extends AbstractITextPdfView {
    @Override
    protected void buildPdfDocument(Map<String, Object> model,
                                    Document document,
                                    PdfWriter writer,
                                    HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        Chapter chapter = new Chapter(new Paragraph("Spring message"), 1);
        chapter.add(new Paragraph((String) model.get("message")));

        document.add(chapter);
    }
}
