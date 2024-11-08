package store.file;

import store.file.dto.SaveDto;

import java.util.List;

public interface FileParser<T extends SaveDto> {

    List<T> parse(String filePath) throws Exception;
}
