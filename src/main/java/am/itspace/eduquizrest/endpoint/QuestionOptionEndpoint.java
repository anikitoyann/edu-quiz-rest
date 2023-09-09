package am.itspace.eduquizrest.endpoint;
import com.example.eduquizcommon.dto.CreateQuestionOptionRequestDto;
import com.example.eduquizcommon.dto.QuestionOptionDto;
import com.example.eduquizcommon.entity.QuestionOption;
import com.example.eduquizcommon.mapper.mapper.QuestionOptionMapper;
import com.example.eduquizcommon.service.QuestionOptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/option")
@RequiredArgsConstructor
public class QuestionOptionEndpoint {
    private final QuestionOptionService questionOptionService;
    private final QuestionOptionMapper questionOptionMapper;

    @GetMapping
    public ResponseEntity<List<QuestionOptionDto>> getAllOptions() {
        List<QuestionOption> all = questionOptionService.findAll();
        if (all.size() == 0) {
            return ResponseEntity.notFound().build();
        }
        List<QuestionOptionDto> questionOptionDto = questionOptionMapper.mapListToDtos(all);
        return ResponseEntity.ok(questionOptionDto);
    }
    @PostMapping("/add")
    public ResponseEntity<QuestionOptionDto> create(@RequestBody CreateQuestionOptionRequestDto createQuestionOptionRequestDto) {
        QuestionOption questionOption= questionOptionService.save(questionOptionMapper.map(createQuestionOptionRequestDto));
        return ResponseEntity.ok(questionOptionMapper.mapToDto(questionOption));
    }

    @GetMapping("/{questionId}")
    public String viewOptions(@PathVariable("questionId") int questionId, Model model) {
        List<QuestionOption> options = questionOptionService.getAllQuestionOptionByQuestionId(questionId);
        model.addAttribute("options", options);
        return "viewOptions";
    }
    @GetMapping("/view/{questionId}")
    public ResponseEntity<QuestionOption> update(@PathVariable("id") int id ) {
        List<QuestionOption> byId = questionOptionService.getAllQuestionOptionByQuestionId(id);
        if (byId.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok((QuestionOption) questionOptionService.getAllQuestionOptionByQuestionId(id));
    }
}
