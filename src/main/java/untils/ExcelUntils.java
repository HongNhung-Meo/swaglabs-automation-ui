package untils;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class ExcelUntils {
    public static List<Map<String, String>> readExcelData(String filePath, String sheetName) {
        List<Map<String, String>> data = new ArrayList<>();
        try (FileInputStream file = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(file)) {

            // Lấy sheet theo tên, nếu không tìm thấy sẽ báo lỗi
            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet '" + sheetName + "' không tồn tại trong file " + filePath);
            }

            // Lấy tiêu đề cột từ hàng đầu tiên
            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new IllegalArgumentException("Sheet '" + sheetName + "' không có dữ liệu hàng tiêu đề");
            }

            DataFormatter dataFormatter = new DataFormatter();
            int colCount = headerRow.getLastCellNum();

            // Duyệt qua các hàng còn lại
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue; // Bỏ qua hàng trống

                Map<String, String> rowData = new LinkedHashMap<>();
                boolean hasValue = false; // Biến kiểm tra xem hàng có giá trị không
                for (int j = 0; j < colCount; j++) {
                    String columnHeader = dataFormatter.formatCellValue(headerRow.getCell(j)).trim();
                    String cellValue = "";
                    if (row.getCell(j) != null) {
                        cellValue = dataFormatter.formatCellValue(row.getCell(j)).trim();
                    }
                    if (!cellValue.isEmpty()) {
                        hasValue = true; // Phát hiện hàng có ít nhất một giá trị không rỗng
                    }
                    rowData.put(columnHeader, cellValue); // Key là tiêu đề cột, Value là dữ liệu ô
                }
                // Chỉ thêm hàng vào danh sách nếu có giá trị
                if (hasValue) {
                    data.add(rowData);
                }
            }

        } catch (IOException e) {
            System.err.println("Đã xảy ra lỗi khi đọc file Excel: " + e.getMessage());
        }
        return data;
    }
}
