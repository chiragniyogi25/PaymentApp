package com.miniproject.payment.app.pdfExporter;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.miniproject.payment.app.entity.Transactions;

public class StatementPDFExporter {
    private List<Transactions> transactionsList;

    public StatementPDFExporter(List<Transactions> transactionsList) {
        this.transactionsList = transactionsList;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(3);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Description", font));

        table.addCell(cell);

        cell.setPhrase(new Phrase("Transaction Date", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Amount", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Opening Balance", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Closing Balance", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Type", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        for (Transactions t : transactionsList) {
            table.addCell(t.getDescription());
            table.addCell(String.valueOf(t.getTransactionDate()));
            table.addCell(String.valueOf(t.getAmount()+""));
            table.addCell(String.valueOf(t.getOpeningBal()+""));
            table.addCell(String.valueOf(t.getClosingBal()+""));
            table.addCell(t.getType());
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(15);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("Your Statement", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {2.0f, 3.5f, 1.5f, 1.5f, 1.5f,1.0f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();

    }
}
