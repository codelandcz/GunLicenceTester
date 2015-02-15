package cz.codeland.gunlicencetester;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class GunLicencePDFTextExtractorTest
{
  private String          text;
  private FileInputStream pdfFile;

  @Before
  public void setUp() throws Exception
  {
    String testPdfPath = "src/test/resources/testfile.pdf";
    pdfFile = new FileInputStream(new File(testPdfPath));
    text = "Sentence in a testing PDF created by MS Word.";
  }

  @Test
  public void extract_ExtractTextFromTestPDF_ReturnString() throws Exception
  {
    // Given
    PDFTextExtractor extractor = new GunLicencePDFTextExtractor();
    // When
    String result = extractor.extract(pdfFile);
    // Then
    Assert.assertTrue(text.equals(result));
  }

  @Test(expected = NullPointerException.class)
  public void createPdfInMemory_NullFileGiven_NullPointerException() throws Exception
  {
    // Given
    GunLicencePDFTextExtractor extractor = new GunLicencePDFTextExtractor();
    // When
    extractor.extract(null);
  }

  @Test(expected = IOException.class)
  public void createPdfInMemory_WrongFileGiven_IOException() throws Exception
  {
    // Given
    GunLicencePDFTextExtractor extractor = new GunLicencePDFTextExtractor();
    // When
    extractor.extract(new FileInputStream(new File("unexistingFileName.pdf")));
  }

  @Test
  public void readResourceFile_ReadFileFromResourceFolder_InputStream() throws Exception
  {
    // Given
    final String absName = "/testfile.pdf";
    GunLicencePDFTextExtractor extractor = new GunLicencePDFTextExtractor();
    // When
    InputStream resourcePdf = extractor.readResourceFile(absName);
    String result = extractor.extract(resourcePdf);
    resourcePdf.close();
    // Then
    Assert.assertTrue(text.equals(result));
  }
}
