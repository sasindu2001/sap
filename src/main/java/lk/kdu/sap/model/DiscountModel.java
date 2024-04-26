package lk.kdu.sap.model;

import lk.kdu.sap.db.DBConnection;
import lk.kdu.sap.dto.DiscountModelDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sasindu Malshan
 * @project SAP
 * @date 4/26/2024
 */

public class DiscountModel {

    public static List<DiscountModelDTO> findDiscount() {

        List<DiscountModelDTO> list = new ArrayList<>();
        try {
            ResultSet resultSet1 = DBConnection.getInstance().getConnection().prepareStatement("SELECT invoice_number, SUM(quantity) as sum  , c.customer_code,c.customer_name  FROM invoice_detail INNER JOIN invoice i on invoice_detail.invoice_number = i.inv_number INNER JOIN customers c ON  c.customer_code= i.customer_code GROUP BY invoice_number").executeQuery();
            while (resultSet1.next()) {
                DiscountModelDTO build = DiscountModelDTO.builder()
                        .name(resultSet1.getString("customer_name"))
                        .no(resultSet1.getString("invoice_number"))
                        .code(resultSet1.getString("customer_code"))
                        .discount("0")
                        .build();
                PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement("SELECT c.customer_name ,id.invoice_number ,i.customer_code, sd.value,id.quantity ,ts.`from`,ts.`to`,sd.schema_id from invoice_detail id INNER JOIN invoice i on id.invoice_number = i.inv_number INNER JOIN schema_detail sd on sd.material_group= id.material_group INNER JOIN time_slots ts ON sd.time_slots = ts.id  INNER JOIN customers c ON i.customer_code = c.customer_code WHERE invoice_number=? AND ? BETWEEN ts.`from` AND ts.`to`GROUP BY sd.schema_id");
                statement.setObject(1, resultSet1.getString("invoice_number"));
                statement.setObject(2, resultSet1.getString("sum"));

                ResultSet resultSet = statement.executeQuery();
                double discount = 0;

                while (resultSet.next()) {
                    discount = discount + Double.parseDouble(resultSet.getString("value")) * Integer.parseInt(resultSet.getString("quantity"));
                   /* build = DiscountModelDTO.builder()
                            .name(resultSet.getString("customer_name"))
                            .no(resultSet.getString("invoice_number"))
                            .code(resultSet.getString("customer_code"))
                            .discount(String.valueOf(discount))
                            .build();*/
                    build.setDiscount(String.valueOf(discount));
                }
                list.add(build);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<String> findCustomerCode() {
        List<String> list = new ArrayList<>();
        list.add("All Customers");
        try {
            ResultSet resultSet1 = DBConnection.getInstance().getConnection().prepareStatement("SELECT customer_code FROM customers").executeQuery();
            while (resultSet1.next()){
                list.add(resultSet1.getString("customer_code"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<DiscountModelDTO> findByCustomerCode(String code) {
        List<DiscountModelDTO> list = new ArrayList<>();
        try {
            PreparedStatement statement1 = DBConnection.getInstance().getConnection().prepareStatement("SELECT invoice_number, SUM(quantity) as sum  , c.customer_code,c.customer_name  FROM invoice_detail INNER JOIN invoice i on invoice_detail.invoice_number = i.inv_number INNER JOIN customers c ON  c.customer_code= i.customer_code WHERE c.customer_code= ? GROUP BY invoice_number ");
            statement1.setObject(1,code);
            ResultSet resultSet1 = statement1.executeQuery();
            while (resultSet1.next()) {
                DiscountModelDTO build = DiscountModelDTO.builder()
                        .name(resultSet1.getString("customer_name"))
                        .no(resultSet1.getString("invoice_number"))
                        .code(resultSet1.getString("customer_code"))
                        .discount("0")
                        .build();
                PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement("SELECT c.customer_name ,id.invoice_number ,i.customer_code, sd.value,id.quantity ,ts.`from`,ts.`to`,sd.schema_id from invoice_detail id INNER JOIN invoice i on id.invoice_number = i.inv_number INNER JOIN schema_detail sd on sd.material_group= id.material_group INNER JOIN time_slots ts ON sd.time_slots = ts.id  INNER JOIN customers c ON i.customer_code = c.customer_code WHERE invoice_number=? AND ? BETWEEN ts.`from` AND ts.`to`GROUP BY sd.schema_id");
                statement.setObject(1, resultSet1.getString("invoice_number"));
                statement.setObject(2, resultSet1.getString("sum"));

                ResultSet resultSet = statement.executeQuery();
                double discount = 0;

                while (resultSet.next()) {
                    discount = discount + Double.parseDouble(resultSet.getString("value")) * Integer.parseInt(resultSet.getString("quantity"));
                   /* build = DiscountModelDTO.builder()
                            .name(resultSet.getString("customer_name"))
                            .no(resultSet.getString("invoice_number"))
                            .code(resultSet.getString("customer_code"))
                            .discount(String.valueOf(discount))
                            .build();*/
                    build.setDiscount(String.valueOf(discount));
                }
                list.add(build);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }
}
