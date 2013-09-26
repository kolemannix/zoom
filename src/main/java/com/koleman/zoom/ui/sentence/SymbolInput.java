package com.koleman.zoom.ui.sentence;

import com.koleman.zoom.logic.parse.token.Token;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

/**
 * Author Koleman Nix
 * Created On 9/25/13 at 11:55 PM
 */
public class SymbolInput extends CustomComponent {

    private SymbolInputField inputField;
    private Label outputArea;

//    @Autowired
//    WorldService worldService;

    public SymbolInput() {
        inputField = new SymbolInputField(this);
        outputArea = new Label();

        addComponent(inputField);
        addComponent(outputArea);
    }


    public void addToken(Token token) {
//        worldService.addTokenToSelectedSentence(token);
        System.out.println("Added token" + token.getSymbol() + " to sentence");
        System.out.println(token.getPersistentRepresentation());
    }
}
