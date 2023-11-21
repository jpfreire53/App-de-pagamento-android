package com.example.appfinalcomcielo.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.appfinalcomcielo.model.Fatura;
import com.example.appfinalcomcielo.views.activity.HomeActivity;

import java.util.UUID;

import cielo.orders.domain.CheckoutRequest;
import cielo.orders.domain.Credentials;
import cielo.orders.domain.Order;
import cielo.sdk.order.OrderManager;
import cielo.sdk.order.ServiceBindListener;
import cielo.sdk.order.payment.PaymentCode;
import cielo.sdk.order.payment.PaymentError;
import cielo.sdk.order.payment.PaymentListener;

public class CieloUtil {

    public static CieloUtil uniqueInstance;
    private Context context;
    private OrderManager orderManager;
    private Order order;
    private long price;

    private CieloUtil(Context context) {
        this.context = context;
    }

    public static synchronized CieloUtil getInstance(Context context) {
        if (uniqueInstance == null) {
            uniqueInstance = new CieloUtil(context);
        }
        return uniqueInstance;

    }

    public void iniciar() {
        Credentials credentials = new Credentials("ImzmzKnYTCXRfGfSrAnX6mqgd9OylPmpz9hwwQirKiGg2Y7gc9", "N3oCpB1geSr3xGC6WD52o74D4S4Cf5Y33W7vKZR0eqFKM5YS8l");
        orderManager = new OrderManager(credentials, context);

        ServiceBindListener serviceBindListener = new ServiceBindListener() {

            @Override
            public void onServiceBoundError(Throwable throwable) {
                Log.d("TAG", "onServiceBoundError: " + throwable.getMessage());
            }

            @Override
            public void onServiceBound() {
                Log.d("TAG", "onServiceBoundSuccess: ");

            }

            @Override
            public void onServiceUnbound() {
                Log.d("TAG", "onServiceBoundUnbound: ");

            }
        };

        orderManager.bind(context, serviceBindListener);


    }

    public void criarPedido() {
        order = orderManager.createDraftOrder(String.valueOf(UUID.randomUUID()));
        Log.d("TAG", "criarPedido: Pedido Criado");
    }

    public void adicionarPedidos(long price, String product) {
        order.addItem("2891820317391823", product, price, 1, "UNIDADE");
        Log.d("TAG", "adicionarPedidos: PedidoAdicionado");
    }

    public void liberarPedido() { orderManager.placeOrder(order); }

    public void pagamento(Fatura fatura) {
        MyDatabaseHelper myDB = new MyDatabaseHelper(context);
        PaymentListener paymentListener = new PaymentListener() {
            @Override
            public void onStart() {
                Log.d("SDKClient", "O pagamento começou.");

            }

            @Override
            public void onPayment(@NonNull Order order) {
                Log.d("SDKClient", "Um pagamento foi realizado.");
                Log.d("TAG", "ON PAYMENT");
                order = order;
                Toast.makeText(context, "Fatura paga com sucesso", Toast.LENGTH_SHORT).show();
                myDB.deleteOneRow(fatura.getId());
                Intent intent = new Intent(context, HomeActivity.class);
                context.startActivity(intent);

                order.markAsPaid();

                orderManager.updateOrder(order);

            }

            @Override
            public void onCancel() {
                Log.d("SDKClient", "A operação foi cancelada.");

            }

            @Override
            public void onError(@NonNull PaymentError paymentError) {
                Log.d("SDKClient", "Houve um erro no pagamento.");

            }
        };

        int installments = Integer.parseInt(fatura.getParcel());
        if (PaymentCode.valueOf(formaDePagamento(fatura.getIsCredit())) == PaymentCode.DEBITO_AVISTA) {
            installments = 1;
        }

        CheckoutRequest request = new CheckoutRequest.Builder()
                .orderId(order.getId())
                .amount(valorFinal(fatura.getValue()))
                .installments(installments)
                .paymentCode(PaymentCode.valueOf(formaDePagamento(fatura.getIsCredit())))
                .build();
        orderManager.checkoutOrder(request, paymentListener);

    }


    public String formaDePagamento(String isCredit){
        if (isCredit.equals("Débito")){
            isCredit = String.valueOf(PaymentCode.DEBITO_AVISTA);
        } else if (isCredit.equals("Crédito")) {
            isCredit = String.valueOf(PaymentCode.CREDITO_AVISTA);
        }
        return isCredit;

    }
    public long valorFinal(String value){
        if (value.contains("R$")) {
            value = value.replace("R$", "");
        }if(value.contains(",")) {
            value = value.replace(".", "");
        }if(value.contains(".")){
            value = value.replace(",", "");
        }
        return Long.parseLong(value);
    }




}
