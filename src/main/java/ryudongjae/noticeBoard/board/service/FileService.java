package ryudongjae.noticeBoard.board.service;

import org.springframework.stereotype.Service;
import ryudongjae.noticeBoard.board.dto.FileDto;
import ryudongjae.noticeBoard.domain.entity.File;
import ryudongjae.noticeBoard.domain.repository.FileRepository;

import javax.transaction.Transactional;

@Service
public class FileService {

    private FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }
    @Transactional
    public Long saveFile(FileDto fileDto){
        return fileRepository.save(fileDto.toEntity()).getId();

    }
    @Transactional
    public FileDto getFile(Long id){
        File file = fileRepository.findById(id).get();

        FileDto fileDto = FileDto.builder()
                .id(id)
                .origFilename(file.getOrigFilename())
                .filename(file.getFilename())
                .filePath(file.getFilePath())
                .build();
        return fileDto;

    }


}
