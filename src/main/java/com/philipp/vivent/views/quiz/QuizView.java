package com.philipp.vivent.views.quiz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.philipp.vivent.data.Category;
import com.philipp.vivent.data.Question;
import com.philipp.vivent.data.QuestionService;
import com.philipp.vivent.services.CategoryService;
import com.philipp.vivent.views.MainLayout;
import com.philipp.vivent.views.admin.QuestionForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.tabs.TabSheetVariant;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "quiz", layout = MainLayout.class)
@PageTitle("Quiz")
@StyleSheet("context://style.css")
public class QuizView extends VerticalLayout implements HasUrlParameter<String>{
	
	private final CategoryService service;
	private final QuestionService questionService;
	private Category category;
	private List<Question> questions;
	private Map<Integer, Boolean> results; 

	public QuizView(CategoryService service, QuestionService questionService) {
		this.service = service;
		this.questionService = questionService;
	}

	@Override
	public void setParameter(BeforeEvent event, String parameter) {
		Long categoryId = Long.parseLong(parameter);
		category = service.findCategoryById(categoryId);
		questions = service.getQuestions(category);
		constructUI();
	}

	private void constructUI() {
		addClassNames("quiz-view");
		add(buildTitleAndImage(), buildQuestions());
	}
	
	private VerticalLayout buildTitleAndImage() {
		H2 title = new H2(category.getName());
		Image image = createImage(category.getImgUrl(), category.getImgAlt()); 
		image.setHeight("75px");
		image.setWidth("75px");

		VerticalLayout layout = new VerticalLayout(image, title);
		layout.setAlignItems(Alignment.CENTER); 
		layout.setJustifyContentMode(JustifyContentMode.START); 
		layout.setSpacing(true); 

		return layout; 
	}
	
	private TabSheet buildQuestions() {
		TabSheet tabSheet = buildTabSheet();
		
		for (Question question : questions) {
			VerticalLayout questionLayout = buildQuestionLayout(question);
			buildAnswerButtons(questionLayout, tabSheet, question);
			buildButtonLayout(questionLayout, tabSheet, question);

			int index = questions.indexOf(question);
			tabSheet.add(String.valueOf(index + 1), questionLayout);
			tabSheet.getTabAt(index).setEnabled(false);
		}		
		
		tabSheet.getTabAt(0).setEnabled(true);
		return tabSheet;
	}
	
	private void buildAnswerButtons(VerticalLayout questionLayout, TabSheet tabSheet, Question question) {
		List<String> answers = new ArrayList<>(
				List.of(question.getCorrectAnswer(), question.getWrongAnswer1(), question.getWrongAnswer2()));
		Collections.shuffle(answers);
		
		List<Button> answerButtons = new ArrayList<Button>();

		for (String answer : answers) {
			Button answerButton = new Button(answer);
			answerButton.addClickListener(e -> {
				if (answer.equals(question.getCorrectAnswer())) {
					answerButton.addClassName("correct-answer");
					disableOtherButtons(answerButton, answerButtons);
					tabSheet.getTabAt(tabSheet.getSelectedIndex()).addClassName("correct-tab");
				} else {
					answerButton.addClassName("wrong-answer");
					disableOtherButtons(answerButton, answerButtons);
					tabSheet.getTabAt(tabSheet.getSelectedIndex()).addClassName("wrong-tab");
				}

				UI.getCurrent().setPollInterval(500);
				UI.getCurrent().addPollListener(event -> {
					moveToNextTab(tabSheet);
					UI.getCurrent().setPollInterval(-1); 
				});
			});
			answerButtons.add(answerButton);
		}
		questionLayout.add(answerButtons.toArray(new Component[0]));
	}

	private void disableOtherButtons(Button answerButton, List<Button> answerButtons) {
		for (Button button : answerButtons) {
			if (!button.equals(answerButton)) {
				button.setEnabled(false);
			}
		}
	}

	private VerticalLayout buildQuestionLayout(Question question) {
		VerticalLayout layout = new VerticalLayout();
		H2 questionText = new H2(question.getQuestionText());
		layout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
		layout.add(questionText);
		layout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
		return layout;
	}

	private TabSheet buildTabSheet() {
		TabSheet tabSheet = new TabSheet();
		tabSheet.getStyle().set("margin", "auto");
		tabSheet.addThemeVariants(TabSheetVariant.LUMO_TABS_CENTERED);
		tabSheet.setWidthFull();
		return tabSheet;
	}

	private void openEditDialog(Question question) {
		Dialog dialog = new Dialog();
		QuestionForm form = new QuestionForm();
		form.setQuestion(question);
		form.addSaveListener(event -> {
			Question savedQuestion = event.getQuestion();
			question.setQuestionText(savedQuestion.getQuestionText());
			question.setCorrectAnswer(savedQuestion.getCorrectAnswer());
			question.setWrongAnswer1(savedQuestion.getWrongAnswer1());
			question.setWrongAnswer2(savedQuestion.getWrongAnswer2());
			dialog.close();
		});
		
		form.addCloseListener(event -> dialog.close());
		form.addSaveListener(e -> questionService.saveQuestion(question));
	    dialog.add(form);	
		dialog.open();
	}
	
	private void buildButtonLayout(VerticalLayout questionLayout, TabSheet tabSheet, Question question) {
		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.setWidthFull();
		buttonLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);

		Button edit = new Button(new Icon(VaadinIcon.EDIT));
		edit.addClickListener(e -> openEditDialog(question));
		
		Button newQuestionButton = new Button(VaadinIcon.PLUS.create());
		Question newQuestion = new Question();
		newQuestion.setCategory(category);
		newQuestionButton.addClickListener(e -> openEditDialog(newQuestion));

		Button previous = new Button(new Icon(VaadinIcon.ARROW_BACKWARD));
		previous.addClickListener(e -> {
			int selectedIndex = tabSheet.getSelectedIndex();
			if (selectedIndex > 0) {
				tabSheet.setSelectedIndex(selectedIndex - 1);
			}
		});

		Button next = new Button(new Icon(VaadinIcon.ARROW_FORWARD));
		next.addClickListener(e -> {
			int selectedIndex = tabSheet.getSelectedIndex();
			if (selectedIndex < questions.size() - 1) {
				tabSheet.setSelectedIndex(selectedIndex + 1);
			}
		});
		
		buttonLayout.add(previous, edit, newQuestionButton, next);
		questionLayout.add(buttonLayout);
	}

	private void moveToNextTab(TabSheet tabSheet) {
		int selectedIndex = tabSheet.getSelectedIndex();
		if (selectedIndex < questions.size() - 1) {
			tabSheet.getTabAt(selectedIndex + 1).setEnabled(true);
			tabSheet.setSelectedIndex(selectedIndex + 1);
		} else {
//			showQuizResults();
		}
	}

	private Image createImage(String url, String alt) {
		Image image = new Image(url, alt);
		image.setHeight("300px");
		image.getStyle().set("object-fit", "cover");
		image.getStyle().set("width", "auto");
		image.getStyle().set("border-radius", "10px");
		image.setWidthFull();
		return image;
	}
}
