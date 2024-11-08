package store.file;

import store.file.dto.ProductSaveDto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static store.file.exception.ExceptionCode.DTO_NOT_MATCHED;
import static store.file.exception.ExceptionCode.NO_FIELDS;

/**
 * 파일의 첫 줄은 다음과 같다.<br/>
 * name,price,quantity,promotion<br/>
 * 필드의 개수는 항상 4개여야 한다.
 * */
public class ProductCsvFileParser implements FileParser<ProductSaveDto> {

    private static final String delimiter = ",";

    @Override
    public List<ProductSaveDto> parse(String filePath) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            validateFieldSize(readFields(bufferedReader).size());
            return getProductSaveDtos(bufferedReader);
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    private static List<ProductSaveDto> getProductSaveDtos(BufferedReader bufferedReader) {
        return bufferedReader.lines()
                .map(line -> List.of(line.split(delimiter)))
                .filter(params -> params.size() == ProductSaveDto.numberOfFields)
                .map(ProductSaveDto::create)
                .toList();
    }

    private void validateFieldSize(int numberOfParams) throws IOException {
        if (numberOfParams != ProductSaveDto.numberOfFields) {
            throw new IOException(DTO_NOT_MATCHED.message);
        }
    }

    private static List<String> readFields(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();
        if (line == null) {
            throw new IOException(NO_FIELDS.message);
        }
        line = line.strip();
        return List.of(line.split(delimiter));
    }
}
