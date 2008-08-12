/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package medicalcryptoappclient.gui.models;

/**
 *
 * @author Piotrek
 */
public class RoleModel {
    private String role;
    private String name;

    public RoleModel(String role, String name) {
        this.role = role;
        this.name = name;

    }

    public String getRole() {
        return role;
    }

    public String getName() {
        return name;
    }
    
    @Override
    public String toString(){
        return name;
    }
}
