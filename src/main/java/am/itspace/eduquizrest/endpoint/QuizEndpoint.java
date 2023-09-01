package am.itspace.eduquizrest.endpoint;
import com.example.eduquizcommon.dto.CreateQuizRequestDto;
import com.example.eduquizcommon.dto.QuizDto;
import com.example.eduquizcommon.entity.Quiz;
import com.example.eduquizcommon.mapper.mapper.QuizMapper;
import com.example.eduquizcommon.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/quiz")
public class QuizEndpoint {
    private final QuizService quizService;
    private  final QuizMapper quizMapper;

    @PostMapping("/add")
    public ResponseEntity<QuizDto> create(@RequestBody CreateQuizRequestDto createQuizRequestDto) {
        Quiz quiz = quizService.save(quizMapper.map(createQuizRequestDto));
        return ResponseEntity.ok(quizMapper.mapToDto(quiz));
    }
    @GetMapping
    public ResponseEntity<List<QuizDto>> getAllQuiz() {
        List<Quiz> all = quizService.findAll();
        if (all.size() == 0) {
            return ResponseEntity.notFound().build();
        }
        List<QuizDto> quizDto = quizMapper.mapListToDtos(all);
        return ResponseEntity.ok(quizDto);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") int id) {
        if (quizService.existsById(id)) {
            quizService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
}
    @GetMapping("/{id}")
    public ResponseEntity<Quiz> singleItem(@PathVariable("id") int id) {
        Optional<Quiz> byId = quizService.findById(id);
        if (byId.isPresent()) {
            Quiz quiz = byId.get();
            return ResponseEntity.ok(quiz);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Quiz> update(@PathVariable("id") int id, @RequestBody Quiz quiz) {
        Optional<Quiz> byId = quizService.findById(id);
        if (byId.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Quiz quizDb = byId.get();
        if (quiz.getTitle()!= null && !quiz.getTitle().isEmpty()) {
            quizDb.setTitle(quiz.getTitle());
        }
        if (quiz.getCreatedDateTime() != null) {
            quizDb.setCreatedDateTime(quiz.getCreatedDateTime());
        }
        return ResponseEntity.ok(quizService.save(quizDb));
    }
}
