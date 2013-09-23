package com.koleman.zoom.ui.sentence;

import com.koleman.zoom.business.service.WorldService;
import com.vaadin.event.Action;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Author Koleman Nix
 * Computing ID: jkn3wn
 * Created On 6/12/13
 * Assignment: Homework
 * Other Collaborators: None
 */
@Component
public class SentenceViewer extends Panel {

    private Button newSentenceButton;
    private Button deleteSentenceButton;

    @Autowired
    private SentenceTable sentenceTable;

    @Autowired
    private WorldService worldService;

    @Autowired
    Action.Handler actionHandler;

    @PostConstruct
    protected void doInit() {
        setWidth("680px");
        setHeight("200px");
        setCaption("Sentences");
        HorizontalLayout layout = new HorizontalLayout();
        newSentenceButton = new Button("+", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                worldService.newSentence();
            }
        });
        deleteSentenceButton = new Button("-", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                worldService.deleteSentence(sentenceTable.getSelectedSentence());
            }
        });

        layout.addComponent(newSentenceButton);
        layout.addComponent(deleteSentenceButton);
        addComponent(sentenceTable);
        addComponent(layout);

        addActionHandler(actionHandler);
    }
}
