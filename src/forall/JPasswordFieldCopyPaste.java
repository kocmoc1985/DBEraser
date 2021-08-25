/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forall;

import javax.swing.JPasswordField;
import javax.swing.Action;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.text.DefaultEditorKit;

/**
 *
 * @author KOCMOC
 */
public class JPasswordFieldCopyPaste extends JPasswordField {

    private static final boolean SHORT_HAND_COMMANDS = false;
    private static final boolean ENG = false;

    public JPasswordFieldCopyPaste() {
        super();
        init();
    }

    private void init() {
        JPopupMenu menu = new JPopupMenu();
        //
//        Action cut = new DefaultEditorKit.CutAction();
//        cut.putValue(Action.NAME, ENG ? "Cut" : "Klipp ut");
//        addShortHandCommands(cut, "control X");
//        menu.add(cut);
//        //
//        Action copy = new DefaultEditorKit.CopyAction();
//        copy.putValue(Action.NAME, ENG ? "Copy" : "Kopiera");
//        addShortHandCommands(copy, "control C");
//        menu.add(copy);

        Action paste = new DefaultEditorKit.PasteAction();
        paste.putValue(Action.NAME, ENG ? "Paste" : "Klistra in");
        addShortHandCommands(paste, "control V");
        menu.add(paste);

//        Action selectAll = new JTextFieldCopyPaste.SelectAll();
//        menu.add(selectAll);

        setComponentPopupMenu(menu);
    }

    private void addShortHandCommands(Action action, String command) {
        if (SHORT_HAND_COMMANDS) {
            action.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(command));
        }
    }

//    static class SelectAll extends TextAction {
//
//        public SelectAll() {
//            super("Select All");
//            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
//        }
//
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            JTextComponent component = getFocusedComponent();
//            component.selectAll();
//            component.requestFocusInWindow();
//        }
//    }
}
