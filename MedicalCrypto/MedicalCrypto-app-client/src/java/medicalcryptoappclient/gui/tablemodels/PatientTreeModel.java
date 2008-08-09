/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package medicalcryptoappclient.gui.tablemodels;

import entities.medical.dto.PersonsDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Piotrek
 */
public class PatientTreeModel {

    PersonsDTO user;
    private HashMap data;

    public PatientTreeModel(PersonsDTO user, HashMap data) {
        this.user = user;
        this.data = data;
    }

    public JTree getJTree() {
        Set keySet = this.getData().keySet();
        List keyList = new ArrayList(keySet);
        Collections.sort(keyList);
        DefaultMutableTreeNode top = new DefaultMutableTreeNode(user);
        for (int j = 0; j < keyList.size(); j++) {
            Object key = keyList.get(j);
            List valueList = getData().get(key);
            Collections.sort(valueList);
            DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(key);
            for (int i = 0; i < valueList.size(); i++) {
                Object value = valueList.get(i);
                treeNode.add(new DefaultMutableTreeNode(value));
            }
            top.add(treeNode);
        }
        return new JTree(top);
    }
    
    public DefaultMutableTreeNode addNodes(DefaultMutableTreeNode parent, List nodes){
        for (int i = 0; i < nodes.size(); i++) {
            Object object = nodes.get(i);
            parent.add(parent);
        }
        return parent;
    }
    
    public DefaultMutableTreeNode addToRoot(DefaultMutableTreeNode root, DefaultMutableTreeNode nodes){
        root.add(nodes);
        return root;
    }

    public HashMap<Object, List> getData() {
        return data;
    }
}
