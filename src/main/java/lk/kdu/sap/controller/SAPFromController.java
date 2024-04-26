package lk.kdu.sap.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.kdu.sap.dto.DiscountModelDTO;
import lk.kdu.sap.model.DiscountModel;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Sasindu Malshan
 * @project SAP
 * @date 4/26/2024
 */

public class SAPFromController implements Initializable {

    public TableView tblDisCount;
    public TableColumn colCode;
    public TableColumn colNo;
    public TableColumn colDisCount;
    public TableColumn colName;
    public JFXComboBox cmdId;
    ObservableList<DiscountModelDTO> list= FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colNo.setCellValueFactory(new PropertyValueFactory<>("no"));
        colDisCount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tblDisCount.setItems(list);

        loadAll();

        cmdId.getItems().addAll(DiscountModel.findCustomerCode());
        cmdId.setValue("All Customers");
    }

    private void loadAll() {
        tblDisCount.getItems().clear();
        List<DiscountModelDTO> discountModelDTOS = DiscountModel.findDiscount();
        tblDisCount.getItems().addAll(discountModelDTOS);
    }

    public void calOnAction(ActionEvent actionEvent) {
        if (cmdId.getValue().equals("All Customers")){
            loadAll();
        }else {
            List<DiscountModelDTO> byCustomerCode = DiscountModel.findByCustomerCode(String.valueOf(cmdId.getValue()));
            tblDisCount.getItems().clear();
            tblDisCount.getItems().addAll(byCustomerCode);
        }
    }
}
