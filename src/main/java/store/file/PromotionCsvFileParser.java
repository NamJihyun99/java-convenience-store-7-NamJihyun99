package store.file;

import store.file.dto.ProductSaveDto;
import store.file.dto.PromotionSaveDto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static store.file.exception.ExceptionCode.DTO_NOT_MATCHED;
import static store.file.exception.ExceptionCode.NO_FIELDS;

public class PromotionCsvFileParser implements FileParser<PromotionSaveDto> {

    private static final String delimiter = ",";

    @Override
    public List<PromotionSaveDto> parse(String filePath) throws Exception {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            validateFieldSize(readFields(bufferedReader).size());
            return getPromotionSaveDtos(bufferedReader);
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    private static List<PromotionSaveDto> getPromotionSaveDtos(BufferedReader bufferedReader) {
        return bufferedReader.lines()
                .map(line -> List.of(line.split(delimiter)))
                .filter(params -> params.size() == PromotionSaveDto.numberOfFields)
                .map(PromotionSaveDto::create)
                .toList();
    }

    private void validateFieldSize(int numberOfParams) throws IOException {
        if (numberOfParams != PromotionSaveDto.numberOfFields) {
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
