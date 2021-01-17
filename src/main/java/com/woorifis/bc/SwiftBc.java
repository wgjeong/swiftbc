package com.woorifis.bc;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Transaction;

import org.hyperledger.fabric.contract.annotation.Contact;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.License;

import static java.nio.charset.StandardCharsets.UTF_8;

@Contract(name = "SwiftBc",
        info = @Info(title = "Swift Blockchain",
                description = "Swift Blockchain SmartContract",
                version = "0.0.1",
                license =
                @License(name = "SPDX-License-Identifier: Apache-2.0",
                        url = "https://github.com/wgjeong/swiftbc"),
                contact =  @Contact(email = "dobby@woorifis.com",
                        name = "JeongWongi",
                        url = "empty")))
@Default
public class SwiftBc implements ContractInterface {
    public SwiftBc() {
    }

    @Transaction()
    public boolean paymentOrderExists(Context ctx, String swiftCode) {
        isEmpty( "swiftCode", swiftCode );
        byte[] buffer = ctx.getStub().getState(swiftCode);
        return (buffer != null && buffer.length > 0);
    }

    @Transaction()
    public void createPaymentOrder( Context ctx
            , String  swiftCode
            , String  date
            , String  amount
            , String  currency
            , String  ordererName
            , String  ordererAddress
            , String  ordererId
            , String  receiverAccount
            , String  receiverName
            , String  receiverAddress
            , String  remittanceInformation
            , String  additionalInstruction
            , String  chargeDetail ) {

        if( validationParam(swiftCode, date, amount, currency, ordererName, ordererAddress, ordererId, receiverAccount, receiverName, receiverAddress, remittanceInformation, chargeDetail) ){

            if( paymentOrderExists(ctx,swiftCode) )
                throw new RuntimeException("The Payment Order (" + swiftCode + ") already exists");
            PaymentOrder paymentOrder = new PaymentOrder(swiftCode, date, amount, currency, ordererName, ordererAddress, ordererId, receiverAccount, receiverName, receiverAddress, remittanceInformation, additionalInstruction, chargeDetail);
            ctx.getStub().putState(swiftCode, paymentOrder.toJSONString().getBytes(UTF_8));
        }
    }

    private boolean validationParam( String swiftCode, String date, String amount, String currency, String ordererName, String ordererAddress, String ordererId, String receiverAccount, String receiverName, String receiverAddress, String remittanceInformation, String chargeDetail ){
        isEmpty( "swiftCode"            , swiftCode             );
        isEmpty( "date"                 , date                  );
        isEmpty( "amount"               , amount                );
        isEmpty( "currency"             , currency              );
        isEmpty( "ordererName"          , ordererName           );
        isEmpty( "ordererAddress"       , ordererAddress        );
        isEmpty( "ordererId"            , ordererId             );
        isEmpty( "receiverName"         , receiverName          );
        isEmpty( "receiverAccount"      , receiverAccount       );
        isEmpty( "receiverAddress"      , receiverAddress       );
        isEmpty( "remittanceInformation", remittanceInformation );
        isEmpty( "chargeDetail"         , chargeDetail          );
        return true;
    }

    private void isEmpty( String paramName, String param ){
        if( null == param || "".equals(param) || ("amount".equals(paramName) && "0".equals(param)) ){
            throw new RuntimeException(paramName + " is empty");
        }
    }

    @Transaction()
    public PaymentOrder readPaymentOrder(Context ctx, String swiftCode) {
        if (!paymentOrderExists(ctx,swiftCode)) {
            throw new RuntimeException("The Payment Order ("+swiftCode+") does not exist");
        }
        return PaymentOrder.fromJSONString(new String(ctx.getStub().getState(swiftCode),UTF_8));
    }
}