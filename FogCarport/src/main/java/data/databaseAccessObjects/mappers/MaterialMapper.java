/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.databaseAccessObjects.mappers;

/**
 *
 * @author Camilla
 */
public class MaterialMapper {
    private static MaterialMapper materialMapper;
    
    private MaterialMapper() {

    }

    public static MaterialMapper getInstance() {
        if (materialMapper == null) {
            materialMapper = new MaterialMapper();
        }
        return materialMapper;
    }
}

