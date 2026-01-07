package com.datasoft.bkash.ea.utils.queryModule;

import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
public class AccountQueryPdfExporter {
    //    private QueryViewPdfDto queryViewPdfDto;
    public AccountQueryPdfExporter() {
        //this.queryViewPdfDto = queryViewPdfDto;
    }

    private void writeTableHeader(PdfPTable table, List list) {
        PdfPCell cell = new PdfPCell();
        cell.setPadding(8);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setSize(12);

        Map<String, Object> map = (Map<String, Object>) list.get(0);

        for (String key : map.keySet()) {
            cell.setPhrase(new Phrase(key, font));
            table.addCell(cell);
        }

    }

    private void writeTableData(PdfPTable table, List list) {
        int i = 0;
        for (Object l : list) {
//           table.addCell(String.valueOf(i));
            Map<String, Object> map = (Map<String, Object>) l;
            for (String key : map.keySet()) {
                if (map.get(key) != null) table.addCell(map.get(key).toString());
                else {
                    table.addCell("");
                }
            }
            i++;
        }
    }

//    public void export(String filePath, List<Object> resultList, String[] accountNumbers) throws DocumentException, IOException {
//        Document document = new Document(PageSize.A3);
//        PdfWriter.getInstance(document, Files.newOutputStream(Paths.get(filePath)));
//
//        document.open();
//        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
//        font.setSize(12);
//
//        Font font2 = FontFactory.getFont(FontFactory.HELVETICA);
//        font2.setSize(12);
//
//        Font linkFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.UNDERLINE, new Color(0, 0, 255));
//        for (int i = 0; i < resultList.size(); i++) {
//
//
//            Chunk underlined = new Chunk("\n" + accountNumbers[i] + "\n ", font2);
//            underlined.setUnderline(0.2f, -2f);
//            document.add(underlined);
//
//            HashMap<String, Object> resultMap = (HashMap<String, Object>) resultList.get(i);
//            for (Map.Entry<String, Object> set : resultMap.entrySet()) {
//
//
//                List<Map<String, String>> list = new ArrayList<>();
//
//                if (set.getValue() != null) {
//                    list = ((ArrayList) set.getValue());
//                }
//
//                if (list.size() > 0 && !set.getKey().contains("graph")) {
//
//
//                    //remove inv_remarks json in control report data
//                    if (set.getKey().equals("control_report")) {
//
//                        try {
//
//                            ArrayList<LinkedCaseInsensitiveMap> crData = (ArrayList<LinkedCaseInsensitiveMap>) set.getValue();
//
//                            crData.forEach(val -> {
//                                val.put("inv_remarks", val.get("inv_remarks_count"));
//                                val.remove("inv_remarks_count");
//                            });
//
//                        } catch (Exception ex) {
//                        }
//                    }
//
//                    //remove allegation_type json and fix spelling investigation
//                    if (set.getKey().equals("investigation")) {
//
//                        try {
//
//                            ArrayList<LinkedCaseInsensitiveMap> crData = (ArrayList<LinkedCaseInsensitiveMap>) set.getValue();
//
//                            crData.forEach(val -> {
//                                val.put("allegation_type", val.get("alegation_type"));
//                                val.remove("alegation_type");
//                            });
//
//                        } catch (Exception ex) {
//                        }
//                    }
//
//
//                    Paragraph p = new Paragraph("\n" + set.getKey() + "\n ", font2);
//                    document.add(p);
//                    Map<String, String> map = list.get(0);
//                    PdfPTable table = new PdfPTable(map.size());
//                    table.setWidthPercentage(100f);
//                    writeTableHeader(table, list);
//                    writeTableData(table, list);
//                    document.add(table);
//                }
//
//
//            }
//        }
//        document.close();
//    }

    public void export(String filePath, List<Object> accountList) throws DocumentException, IOException {
        Document document = new Document(PageSize.A3);
        PdfWriter.getInstance(document, Files.newOutputStream(Paths.get(filePath)));
        document.open();

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(12);
        Font font2 = FontFactory.getFont(FontFactory.HELVETICA);
        font2.setSize(12);

        boolean contentAdded = false;

        for (Object accountObj : accountList) {
            if (!(accountObj instanceof Map)) continue;

            Map<String, Object> accountMap = (Map<String, Object>) accountObj;

            for (Map.Entry<String, Object> accountEntry : accountMap.entrySet()) {
                String accountNumber = accountEntry.getKey();
                Map<String, Object> txnMap = (Map<String, Object>) accountEntry.getValue();
                String headerText;
//                if (accountMap.get(accountNumber) instanceof Map && tableTypes.equals("summaryTransactionTable")) {
//                    headerText = "Photo ID Number: " + accountNumber;
//                } else {
                    headerText = accountNumber;
//                }
//                Chunk underlined = new Chunk(headerText, font2);


                Paragraph l = new Paragraph("\n" +  "\n");
                document.add(l);
                Chunk underlined = new Chunk(headerText, font2);
//                Chunk underlined = new Chunk(  accountNumber , font2);
                underlined.setUnderline(0.2f, -2f);
                document.add(underlined);
                document.add(l);
                contentAdded = true;

                for (Map.Entry<String, Object> txnEntry : txnMap.entrySet()) {
                    String txnType = txnEntry.getKey();
                    Object txnData = txnEntry.getValue();

//                    if (!(txnData instanceof List)) continue;

                    if (txnData instanceof Map<?, ?> dataMap) {
                        // Special case: data with headers/rows
                        List<String> headers = (List<String>) dataMap.get("headers");
                        List<String> keys = (List<String>) dataMap.get("keys");
                        List<Map<String, Object>> rows = (List<Map<String, Object>>) dataMap.get("rows");

                        Paragraph p = new Paragraph("\n" + txnType + "\n\n\n", font2);
                        document.add(p);

                        PdfPTable table = new PdfPTable(headers.size());
                        table.setWidthPercentage(100f);

                        // Write headers
                        for (String header : headers) {
                            PdfPCell cell = new PdfPCell(new Phrase(header, font));
                            cell.setBackgroundColor(Color.LIGHT_GRAY);
                            table.addCell(cell);
                        }

                        // Write rows
                        if (rows == null || rows.isEmpty()) {
                            PdfPCell noDataCell = new PdfPCell(new Phrase("No Data Found", font2));
                            noDataCell.setColspan(headers.size());
                            noDataCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                            table.addCell(noDataCell);
                        } else {
                            for (Map<String, Object> row : rows) {
                                for (String key : keys) {
                                    Object value = row.get(key);
                                    String cellValue = (value == null) ? " " : String.valueOf(value);
                                    table.addCell(new Phrase(cellValue, font2));
                                }
                            }
                        }

                        document.add(table);
                        contentAdded = true;

                    } else{
                        List<Map<String, Object>> tableData = new ArrayList<>();

//                    for (Object item : (List<?>) txnData) {
//                        if (item instanceof Map<?, ?>) {
//                            @SuppressWarnings("unchecked")
//                            Map<String, Object> copy = new LinkedHashMap<>((Map<String, Object>) item); // make a copy
//                            copy.remove("id"); // remove top-level "id" if present
//                            copy.remove("alleged_amount"); // remove top-level "id" if present
////                            copy.remove("id"); // remove top-level "id" if present
////                            copy.remove("id"); // remove top-level "id" if present
//                            tableData.add(copy);
//                        }
//                    }

                        for (Object item : (List<?>) txnData) {
                            if (item instanceof Map<?, ?> mapItem) {
                                Map<String, Object> copy = new LinkedHashMap<>();

                                for (Map.Entry<?, ?> entry : mapItem.entrySet()) {
                                    String key = String.valueOf(entry.getKey()); // force to string
                                    if (!key.equalsIgnoreCase("id") && !key.equalsIgnoreCase("alleged_amount") && !key.equalsIgnoreCase("alleged_count")
                                            && !key.equalsIgnoreCase("not_alleged_amount") && !key.equalsIgnoreCase("not_alleged_count") && !key.equalsIgnoreCase("party_district_name")
                                            && !key.equalsIgnoreCase("agent_number") && !key.equalsIgnoreCase("owners_name")&& !key.equalsIgnoreCase("owners_photo_id")
                                            && !key.equalsIgnoreCase("party_bmcc_code") && !key.equalsIgnoreCase("alleged_status") && !key.equalsIgnoreCase("recommendation")
                                            && !key.equalsIgnoreCase("recommendation_id") && !key.equalsIgnoreCase("transaction_time_filter") && !key.equalsIgnoreCase("segment")) {
                                        copy.put(key, entry.getValue());
                                    }
                                }

                                tableData.add(copy);
                            }
                        }



                        if (!tableData.isEmpty()) {


                            Paragraph p = new Paragraph("\n" + txnType + "\n\n\n", font2);
                            document.add(p);

                            PdfPTable table = new PdfPTable(tableData.get(0).size());
                            table.setWidthPercentage(100f);

                            writeTableHeader(table, tableData);
                            writeTableData(table, tableData);
                            document.add(table);
                            contentAdded = true;
                        }
                        else {
                            Paragraph p = new Paragraph("\n" + txnType + "\n No Data Found!!! \n" + "\n", font2);
                            document.add(p);
                        }
                    }

                }

            }

        }

        if (!contentAdded) {
            Paragraph p = new Paragraph("No Data Available to Display", font2);
            document.add(p);
        }

        document.close();
    }



}

