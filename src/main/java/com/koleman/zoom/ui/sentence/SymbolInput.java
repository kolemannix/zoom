package com.koleman.zoom.ui.sentence;

import com.koleman.zoom.business.service.WorldService;
import com.koleman.zoom.logic.LogicalSymbols;
import com.koleman.zoom.logic.parse.token.Token;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Author Koleman Nix
 * Created On 9/25/13 at 11:55 PM
 */
@Component
public class SymbolInput extends CustomComponent {

    private SymbolInputField inputField;
    private Label notification;

    @Autowired
    WorldService worldService;

    @PostConstruct
    private void doInit() {

        HorizontalLayout layout = new HorizontalLayout();
        setCompositionRoot(layout);
        inputField = new SymbolInputField(this);
        notification = new Label();

        notification.setContentMode(Label.CONTENT_XHTML); // let's make it support HMTL

        layout.addComponent(inputField);
        layout.addComponent(notification);
    }

    public void addToken(Token token) {
        worldService.addTokenToSelectedSentence(token);
        System.out.println("Added token" + token.getSymbol() + " to sentence");
        System.out.println(token.getPersistentRepresentation());
    }

    public void setNotification(String text) {
        notification.setValue(text);
    }

    private class SymbolInputField extends TextField {

        private final SymbolInput parent;

        public SymbolInputField(SymbolInput symbolInput) {
            super(null, "");
            setInputPrompt("(Enter Symbol Here)");
            this.addShortcutListener(new ShortcutListener("Submit Token", ShortcutAction.KeyCode.ENTER, null) {
                @Override
                public void handleAction(Object sender, Object target) {
                    submit();
                }
            });
            this.parent = symbolInput;
        }

        private void submit() {
            Token token = LogicalSymbols.getTokenForSymbol(((String) this.getValue()).trim().toLowerCase());
            if (token != null) {
                parent.addToken(token);
                parent.setNotification("Token submitted");
            } else {
                parent.setNotification("Not a Token");
            }
            this.setValue("");
        }
    }
}
