package cz.codeland.gunlicensetester;

import java.io.IOException;
import java.io.InputStream;

public interface PDFTextExtractor
{
  String extract(InputStream pdfFile) throws IOException;
}
