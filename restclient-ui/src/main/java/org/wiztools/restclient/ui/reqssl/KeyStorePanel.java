package org.wiztools.restclient.ui.reqssl;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.wiztools.restclient.bean.SSLKeyStore;
import org.wiztools.restclient.ui.UIUtil;

/**
 *
 * @author subwiz
 */
public class KeyStorePanel extends JPanel implements KeyStoreListener {
    
    @Inject private KeyStoreDialog jd;
    
    private final JLabel jl = new JLabel();
    private final JTextField jtf = new JTextField(15);
    private final JButton jb_addEdit = new JButton("Add/Edit");
    private final JButton jb_clear = new JButton("Clear");
    
    private SSLKeyStore keyStore = null;
    
    @PostConstruct
    public void init() {
        jtf.setEditable(false);
        
        jd.addKeyStoreListener(this);
        
        jb_addEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jd.setVisible(true);
            }
        });
        
        jb_clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jd.clear();
                jtf.setText("");
            }
        });
        
        JPanel jp_jtf = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        jp_jtf.add(jtf);
        
        setLayout(new BorderLayout());
        
        add(jl, BorderLayout.WEST);
        add(jp_jtf, BorderLayout.CENTER);
        add(UIUtil.getFlowLayoutLeftAlignedMulti(jb_addEdit, jb_clear), BorderLayout.EAST);
    }
    
    public void setLabel(String label) {
        jl.setText(label);
    }
    
    public void setTitle(String title) {
        jd.setTitle(title);
    }

    @Override
    public void ok(SSLKeyStore store) {
        if(store != null) {
            String txt = store.getType() + "; " + store.getFile().getName();
            jtf.setText(txt);
            
            keyStore = store;
        }
    }
    
    @Override
    public void cancel() {
        jd.setKeyStore(keyStore);
    }
    
    public SSLKeyStore getKeyStore() {
        return keyStore;
    }
    
    public void setKeyStore(SSLKeyStore store) {
        jd.setKeyStore(store);
        ok(store);
    }
    
    public void clear() {
        jd.clear();
        ok(null);
    }
}