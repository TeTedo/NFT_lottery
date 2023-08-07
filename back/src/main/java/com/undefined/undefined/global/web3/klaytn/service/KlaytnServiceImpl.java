package com.undefined.undefined.global.web3.klaytn.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Uint;
import org.web3j.abi.datatypes.generated.Uint96;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.Response;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.tx.FastRawTransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

@Service
@Getter
@RequiredArgsConstructor
public class KlaytnServiceImpl implements KlaytnService{

    private final Web3j web3jHttpRpc;
    private final long TX_END_CHECK_DURATION = 3000;
    private final int TX_END_CHECK_RETRY = 3;

    private static final String PRIVATE_KEY = "0xa75b0accba5be6c9e3093897b7ae9fb2d98b11cb70c50e16f7e081b7ec886512";

    private static final String PUBLIC_KEY = "0x39317eB56B5b627E3A840C041c4bBec2089D76F3";

    private static final String CONTRACT_ADDRESS = "0xedE916cA2375F50aEaB50a9cCb92Bb69F8c37438";

    private static BigInteger currentNonce = null;

    @Override
    public void chooseWinner(Long _raffleId, int _randNum) throws IOException {

        BigInteger raffleId = BigInteger.valueOf(_raffleId);
        BigInteger randNum = BigInteger.valueOf(_randNum);

        Function chooseWinnerFunction = new Function(
                "chooseWinner",
                Arrays.asList(
                        new Uint96(raffleId),
                        new Uint(randNum)
                ),
                Collections.emptyList()
        );

        String encodedFunction = FunctionEncoder.encode(chooseWinnerFunction);

        Credentials credentials = Credentials.create(PRIVATE_KEY);

        synchronized (KlaytnServiceImpl.class) {
            if (currentNonce == null) {
                EthGetTransactionCount ethGetTransactionCount = web3jHttpRpc.ethGetTransactionCount(
                        PUBLIC_KEY,
                        DefaultBlockParameterName.PENDING
                ).send();

                if (ethGetTransactionCount.hasError()) {
                    throw new IOException("nonce 실패");
                }

                currentNonce = ethGetTransactionCount.getTransactionCount();
            }
        }

        BigInteger nonce = currentNonce;
        currentNonce = currentNonce.add(BigInteger.ONE);
        System.out.println(nonce + "nonce 값");

        ContractGasProvider gasProvider = new DefaultGasProvider();
        BigInteger baseFee = web3jHttpRpc.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, true).send().getBlock().getBaseFeePerGas();

        RawTransaction rawTransaction = RawTransaction.createTransaction(
                nonce,
                gasProvider.getGasPrice("chooseWinner").add(baseFee),
                gasProvider.getGasLimit("chooseWinner"),
                CONTRACT_ADDRESS,
                BigInteger.ZERO, // no payable
                encodedFunction
        );

        FastRawTransactionManager transactionManager = new FastRawTransactionManager(
                web3jHttpRpc,
                credentials,
                new PollingTransactionReceiptProcessor(web3jHttpRpc,TX_END_CHECK_DURATION, TX_END_CHECK_RETRY )
        );

        try {
            EthSendTransaction transactionReceipt = transactionManager.signAndSend(rawTransaction);

            Response.Error error = transactionReceipt.getError();
            System.out.println(error.getMessage());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
