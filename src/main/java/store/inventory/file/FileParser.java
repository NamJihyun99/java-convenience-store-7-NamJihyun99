package store.inventory.file;

import store.inventory.file.dto.SaveDto;

import java.util.List;

public interface FileParser<T extends SaveDto> {

    List<T> parse(String filePath) throws Exception;
}
