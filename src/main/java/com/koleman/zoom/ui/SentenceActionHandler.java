package com.koleman.zoom.ui;

import com.koleman.zoom.business.service.WorldService;
import com.koleman.zoom.logic.parse.token.Misc;
import com.koleman.zoom.logic.parse.token.Variable;
import com.vaadin.event.Action;
import com.vaadin.event.ShortcutAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Author Koleman Nix
 * Computing ID: jkn3wn
 * Created On 6/27/13
 * Assignment: Homework
 * Other Collaborators: None
 */
@Component
public class SentenceActionHandler implements Action.Handler {

    private List<Action> actions;

    private Action backspaceAction;
    private Action openParenAction;
    private Action closeParenAction;
    private Action aAction;
    private Action bAction;
    private Action cAction;
    private Action dAction;
    private Action eAction;
    private Action fAction;

    private Action uAction;
    private Action vAction;
    private Action wAction;
    private Action xAction;
    private Action yAction;
    private Action zAction;

    @Autowired
    WorldService worldService;

    @Override
    public Action[] getActions(Object target, Object sender) {

        actions = new ArrayList<Action>();

        backspaceAction = new ShortcutAction("Backspace",
                ShortcutAction.KeyCode.BACKSPACE, null);
        openParenAction = new ShortcutAction("(",
                ShortcutAction.KeyCode.NUM9, null);
        closeParenAction = new ShortcutAction(")",
                ShortcutAction.KeyCode.NUM0, null);

        aAction = new ShortcutAction("A", ShortcutAction.KeyCode.A, null);
        bAction = new ShortcutAction("B", ShortcutAction.KeyCode.B, null);
        cAction = new ShortcutAction("C", ShortcutAction.KeyCode.C, null);
        dAction = new ShortcutAction("D", ShortcutAction.KeyCode.D, null);
        eAction = new ShortcutAction("E", ShortcutAction.KeyCode.E, null);
        fAction = new ShortcutAction("F", ShortcutAction.KeyCode.F, null);

        uAction = new ShortcutAction("U", ShortcutAction.KeyCode.U, null);
        vAction = new ShortcutAction("V", ShortcutAction.KeyCode.V, null);
        wAction = new ShortcutAction("W", ShortcutAction.KeyCode.W, null);
        xAction = new ShortcutAction("X", ShortcutAction.KeyCode.X, null);
        yAction = new ShortcutAction("Y", ShortcutAction.KeyCode.Y, null);
        zAction = new ShortcutAction("Z", ShortcutAction.KeyCode.Z, null);

        actions.addAll(Arrays.asList(
                backspaceAction, openParenAction, closeParenAction,
                aAction, bAction, cAction,
                dAction, eAction, fAction,
                uAction, vAction, wAction,
                xAction, yAction, zAction));

        return actions.toArray(new Action[actions.size()]);
    }

    @Override
    public void handleAction(Action action, Object sender, Object target) {
        if (action == backspaceAction) {
            worldService.performBackspaceAction();
        }
        if (action == openParenAction) {
            worldService.addTokenToSelectedSentence(Misc.OPEN);
        }
        if (action == closeParenAction) {
            worldService.addTokenToSelectedSentence(Misc.CLOSE);
        }
        if (action == aAction) worldService.addTokenToSelectedSentence(Variable.A);
        if (action == bAction) worldService.addTokenToSelectedSentence(Variable.B);
        if (action == cAction) worldService.addTokenToSelectedSentence(Variable.C);
        if (action == dAction) worldService.addTokenToSelectedSentence(Variable.D);
        if (action == eAction) worldService.addTokenToSelectedSentence(Variable.E);
        if (action == fAction) worldService.addTokenToSelectedSentence(Variable.F);

        if (action == uAction) worldService.addTokenToSelectedSentence(Variable.U);
        if (action == vAction) worldService.addTokenToSelectedSentence(Variable.V);
        if (action == wAction) worldService.addTokenToSelectedSentence(Variable.W);
        if (action == xAction) worldService.addTokenToSelectedSentence(Variable.X);
        if (action == yAction) worldService.addTokenToSelectedSentence(Variable.Y);
        if (action == zAction) worldService.addTokenToSelectedSentence(Variable.Z);
    }

}
