package com.bankapplication.Bank.Application;

import java.io.File;

import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.MalformedURLException;



import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSmartCopy;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFConversionUtility {
	static String temppath = System.getProperty("java.io.tmpdir");
	
	public static void main(String[] args) throws IOException, DocumentException {
		//File mergePDF = mergePDF(temppath +"TempPDF\\Tempfolder", "mergedFile.pdf");
		File imgPDF = imagetoPDF(temppath + "TempPDF\\Tempfolder\\mergedPDF", temppath + "TempPDF\\Tempfolder");
		System.out.println(imgPDF);
	}

	public static File imagetoPDF(String fileName, String filePath)
			throws MalformedURLException, IOException, DocumentException {

		fileName = fileName + ".pdf";

		File file = new File(fileName);

		Document document = new Document(PageSize.A4, 0, 0, 0, 0);

		PdfWriter.getInstance(document, new FileOutputStream(fileName));
		document.open();
		File files = new File(filePath);
		String[] images = files.list();
		int len = images.length;

		for (int i = 0; i < len; i++) {

			if (images[i].toLowerCase().endsWith(".bmp") || images[i].toLowerCase().endsWith(".jpg")

					|| images[i].toLowerCase().endsWith(".jpeg") || images[i].toLowerCase().endsWith(".gif")

					|| images[i].toLowerCase().endsWith(".png")) {

				String temp = filePath + "\\" + images[i];

				Image img = Image.getInstance(temp);

				img.setAlignment(Image.ALIGN_CENTER);

				document.setPageSize(new Rectangle(img.getWidth(), img.getHeight()));

				document.newPage();

				document.add(img);

			} 
		}
		document.close();
					
		File finaloutput = mergePDF(temppath + "TempPDF\\Tempfolder","mergedFile.pdf");
				
		return finaloutput;
	}

	public static File texttoPDF() {
		return null;
	}

	public static File docstoPDF() {
		return null;
	}

	public static File mergePDF(String directory, String targetFile) throws IOException, DocumentException {
		
		File dir = new File(directory);
        File[] filesToMerge = dir.listFiles(new FilenameFilter() {
            public boolean accept(File file, String fileName) {
                //System.out.println(fileName);
                return fileName.endsWith(".pdf");
            }
        });
        Document document = new Document();
        FileOutputStream outputStream = new FileOutputStream(temppath +"TempPDF\\convertedPDF\\"+targetFile);
        PdfCopy copy = new PdfSmartCopy(document, outputStream);
        document.open();

        for (File inFile : filesToMerge) {
            System.out.println(inFile.getCanonicalPath());
            PdfReader reader = new PdfReader(inFile.getCanonicalPath());
            copy.addDocument(reader);
            reader.close();
        }
        document.close();
		return dir;
	}

}
