package com.philipp.vivent.data;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class QuestionService {
	
	private final QuestionRepository questionRepository;
	
	public QuestionService(QuestionRepository questionRepository) {
		this.questionRepository = questionRepository;
	}
	
	public List<Question> findAllQuestions(String stringFiler) {
		if (stringFiler == null || stringFiler.isEmpty()) {
			List<Question> all = questionRepository.findAll();
			return all;
		} else {
			return questionRepository.search(stringFiler);
		}
	}
	
	public List<Question> findQuestionsByCategoryId(Long categoryId) {
		return questionRepository.findByCategoryId(categoryId);
    }
	public void saveQuestion(Question question) {
        if (question == null) {
            return;
        }
        questionRepository.save(question);
    }
}
