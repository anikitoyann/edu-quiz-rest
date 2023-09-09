package am.itspace.eduquizrest.endpoint;

import com.example.eduquizcommon.dto.AnswerDto;
import com.example.eduquizcommon.dto.CreateAnswerRequestDto;
import com.example.eduquizcommon.entity.Answer;
import com.example.eduquizcommon.mapper.mapper.AnswerMapper;
import com.example.eduquizcommon.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerEndPoint {
    private final AnswerService answerService;
    private final AnswerMapper answerMapper;

    @PostMapping("/add")
    public ResponseEntity<AnswerDto> create(@RequestBody CreateAnswerRequestDto createAnswerRequestDto) {
        Answer answer = answerService.save(answerMapper.map(createAnswerRequestDto));
        return ResponseEntity.ok(answerMapper.mapToDto(answer));
    }

    @GetMapping("/all")
    public ResponseEntity<List<AnswerDto>> getAllAnswer() {
        List<Answer> all = answerService.findAll();
        if (all.size() == 0) {
            return ResponseEntity.notFound().build();
        }
        List<AnswerDto> answerDtos = answerMapper.mapListToDtos(all);
        return ResponseEntity.ok(answerDtos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") int id) {
        if (answerService.existsById(id)) {
            answerService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
