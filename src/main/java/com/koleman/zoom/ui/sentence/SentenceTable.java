package com.koleman.zoom.ui.sentence;

import com.koleman.zoom.business.event.ActiveWorldChangedEvent;
import com.koleman.zoom.business.service.WorldService;
import com.koleman.zoom.logic.evaluate.SentenceState;
import com.koleman.zoom.logic.model.world.ZoomWorld;
import com.koleman.zoom.logic.parse.Sentence;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Table;
import org.dellroad.stuff.vaadin.VaadinApplicationListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Author Koleman Nix
 * Computing ID: jkn3wn
 * Created On 6/18/13
 * Assignment: Homework
 * Other Collaborators: None
 */
@Component
public class SentenceTable extends Table {

    private BeanItemContainer<Sentence> container;

    @Autowired
    WorldService worldService;

    @Autowired
    SimpleApplicationEventMulticaster multicaster;

    VaadinApplicationListener<ActiveWorldChangedEvent> worldChangedListener;

    private Sentence selectedSentence;

    @PostConstruct
    private void doInit() {
        container = new BeanItemContainer<Sentence>(Sentence.class);
        setSortDisabled(true);
        setSelectable(false);
        setContainerDataSource(container);
        setHeight("200px");
        setWidth("600px");
        setVisibleColumns(new String[]{"state", "tokensForDisplay"});
        setColumnHeaders(new String[]{"", ""});
        setColumnWidth("state", 15);
        setCellStyleGenerator(new CellStyleGenerator() {
            @Override
            public String getStyle(Object itemId, Object propertyId) {
                Sentence clickedSentence = container.getItem(itemId).getBean();
                if (propertyId != null && propertyId.equals("state")) {
                    SentenceState state = clickedSentence.getState();
                    if (state == SentenceState.TRUE) return "true";
                    if (state == SentenceState.FALSE) return "false";
                    else return "invalid";
                } else {
                    if (clickedSentence == selectedSentence) {
                        return "selected";
                    } else {
                        return "default";
                    }
                }
            }
        });
        addListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent event) {
                handleItemClickEvent(event);
            }
        });

        worldChangedListener = new VaadinApplicationListener<ActiveWorldChangedEvent>(multicaster, ActiveWorldChangedEvent.class) {
            @Override
            protected void onApplicationEventInternal(ActiveWorldChangedEvent event) {
                repopulate(event.getWorld());
            }
        };
        multicaster.addApplicationListener(worldChangedListener);
    }

    public void repopulate(ZoomWorld world) {
        container.removeAllItems();
        for (Sentence sentence : world.getSentences()) {
            sentence.reEvaluate();
            container.addBean(sentence);
        }
    }

    public void refreshSentence(Sentence sentence) {
        sentence.reEvaluate();
        int index = container.indexOfId(sentence);
        container.removeItem(sentence);
        container.addItemAt(index, sentence);
    }

    public Sentence getSelectedSentence() {
        return this.selectedSentence;
    }

    private void handleItemClickEvent(ItemClickEvent event) {
        this.selectedSentence = container.getItem(event.getItemId()).getBean();
        refreshRenderedCells();
    }
}
