package com.woorifis.bc;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;
import org.json.JSONObject;

import java.math.BigDecimal;

@DataType()
public class PaymentOrder {

    @Property()
    private String swiftCode;               // 참조번호
    @Property()
    private String date;                    // 일자
    @Property()
    private BigDecimal amount;              // 금액
    @Property()
    private String currency;                // 통화
    @Property()
    private String ordererName;             // 송금인 명
    @Property()
    private String ordererAddress;          // 송금인 주소
    @Property()
    private String ordererId;               // 송금인 고객번호
    @Property()
    private String receiverAccount;         // 수취인 계좌번호
    @Property()
    private String receiverName;            // 수취인 명
    @Property()
    private String receiverAddress;         // 수취인 주소
    @Property()
    private String remittanceInformation;   // 송금 목적
    @Property()
    private String additionalInstruction;   // 추가 지시사항
    @Property()
    private String chargeDetail;            // 수수료 징수구분(부담자 구분)

    public PaymentOrder(String swiftCode, String date, String amount, String currency, String ordererName, String ordererAddress, String ordererId, String receiverAccount, String receiverName, String receiverAddress, String remittanceInformation, String additionalInstruction, String chargeDetail) {
        this.swiftCode = swiftCode;
        this.date = date;
        this.amount = new BigDecimal(amount);
        this.currency = currency;
        this.ordererName = ordererName;
        this.ordererAddress = ordererAddress;
        this.ordererId = ordererId;
        this.receiverAccount = receiverAccount;
        this.receiverName = receiverName;
        this.receiverAddress = receiverAddress;
        this.remittanceInformation = remittanceInformation;
        this.additionalInstruction = additionalInstruction;
        this.chargeDetail = chargeDetail;
    }

    public PaymentOrder(){
    }

    public String getOrdererName() {
        return ordererName;
    }
    public void setOrdererName(String ordererName) {
        this.ordererName = ordererName;
    }
    public String getSwiftCode() {
        return swiftCode;
    }
    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public String getOrdererId() {
        return ordererId;
    }
    public void setOrdererId(String ordererId) {
        this.ordererId = ordererId;
    }
    public String getReceiverAccount() {
        return receiverAccount;
    }
    public void setReceiverAccount(String receiverAccount) {
        this.receiverAccount = receiverAccount;
    }
    public String getReceiverName() {
        return receiverName;
    }
    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }
    public String getReceiverAddress() {
        return receiverAddress;
    }
    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }
    public String getRemittanceInformation() {
        return remittanceInformation;
    }
    public void setRemittanceInformation(String remittanceInformation) {
        this.remittanceInformation = remittanceInformation;
    }
    public String getAdditionalInstruction() {
        return additionalInstruction;
    }
    public void setAdditionalInstruction(String additionalInstruction) {
        this.additionalInstruction = additionalInstruction;
    }
    public String getChargeDetail() {
        return chargeDetail;
    }
    public void setChargeDetail(String chargeDetail) {
        this.chargeDetail = chargeDetail;
    }
    public String getOrdererAddress() {
        return ordererAddress;
    }
    public void setOrdererAddress(String ordererAddress) {
        this.ordererAddress = ordererAddress;
    }

    public String toJSONString() {
        return new JSONObject(this).toString();
    }

    public static PaymentOrder fromJSONString(String json) {
        JSONObject paramJson = new JSONObject(json);
        return new PaymentOrder( paramJson.getString("swiftCode"            )
                , paramJson.getString("date"                 )
                , paramJson.getString("amount"               )
                , paramJson.getString("currency"             )
                , paramJson.getString("ordererName"          )
                , paramJson.getString("ordererAddress"       )
                , paramJson.getString("ordererId"            )
                , paramJson.getString("receiverAccount"      )
                , paramJson.getString("receiverName"         )
                , paramJson.getString("receiverAddress"      )
                , paramJson.getString("remittanceInformation")
                , paramJson.getString("additionalInstruction")
                , paramJson.getString("chargeDetail"         )
        );
    }
}