package am.itspace.eduquizrest.endpoint;

import com.example.eduquizcommon.dto.QuestionDto;
import com.example.eduquizcommon.dto.QuizDto;
import com.example.eduquizcommon.entity.Question;
import com.example.eduquizcommon.entity.Quiz;
import com.example.eduquizcommon.mapper.mapper.QuestionMapper;
import com.example.eduquizcommon.service.QuestionService;
import com.example.eduquizcommon.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionEndpoint {
    private final QuestionService questionService;
    private final QuestionMapper questionMapper;

    @GetMapping
    public ResponseEntity<List<QuestionDto>> getAllQuestion() {
        List<Question> all = questionService.findAll();
        if (all.size() == 0) {
            return ResponseEntity.notFound().build();
        }
        List<QuestionDto> questionDto = questionMapper.mapListToDtos(all);
        return ResponseEntity.ok(questionDto);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") int id) {
        if (questionService.existsById(id)) {
            questionService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
