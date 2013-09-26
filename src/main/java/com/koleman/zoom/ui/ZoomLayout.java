package com.koleman.zoom.ui;

import com.koleman.zoom.ui.board.BoardPanel;
import com.koleman.zoom.ui.inspector.Inspector;
import com.koleman.zoom.ui.keyboard.Keyboard;
import com.koleman.zoom.ui.sentence.SentenceTable;
import com.koleman.zoom.ui.sentence.SentenceViewer;
import com.koleman.zoom.ui.sentence.SymbolInput;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Author Koleman Nix
 * Created On 6/11/13
 */
@Component
public class ZoomLayout extends HorizontalLayout {

    @Autowired
    private BoardPanel boardPanel;
    @Autowired
    private Inspector inspector;
    @Autowired
    private Keyboard keyboard;
    @Autowired
    private SymbolInput symbolInput;
    @Autowired
    private SentenceViewer sentenceViewer;
    @Autowired
    SentenceTable sentenceTable;
    @Autowired
    private WorldsPanel worldsPanel;

    @PostConstruct
    protected void buildLayout() {
        setSpacing(true);
        VerticalLayout leftHalf = new VerticalLayout();
        leftHalf.setSpacing(true);
        VerticalLayout rightHalf = new VerticalLayout();
        rightHalf.setSpacing(true);
        leftHalf.addComponent(inspector);
//        leftHalf.addComponent(keyboard);
        leftHalf.addComponent(symbolInput);
        HorizontalLayout derpDerp = new HorizontalLayout();
        derpDerp.setSpacing(true);
        derpDerp.addComponent(boardPanel);
        derpDerp.addComponent(worldsPanel);
        rightHalf.addComponent(derpDerp);
//        rightHalf.addComponent(sentenceViewer);
        rightHalf.addComponent(sentenceTable);
        addComponent(leftHalf);
        addComponent(rightHalf);
    }

}
