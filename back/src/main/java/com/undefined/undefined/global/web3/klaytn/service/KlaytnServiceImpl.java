package com.undefined.undefined.global.web3.klaytn.service;

import com.undefined.undefined.global.web3.klaytn.dto.MultiChooseWinnerDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.tx.FastRawTransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@Getter
@RequiredArgsConstructor
@Slf4j
public class KlaytnServiceImpl implements KlaytnService{

    private final Web3j web3jHttpRpc;
    private final long TX_END_CHECK_DURATION = 3000;
    private final int TX_END_CHECK_RETRY = 3;

    @Value("${wallet.private-key}")
    private String PRIVATE_KEY;

    @Value("${wallet.public-key}")
    private String PUBLIC_KEY;

    @Value("${uri.contract-address}")
    private String CONTRACT_ADDRESS;

    @Override
    public void chooseWinner(List<MultiChooseWinnerDto> dto) throws IOException {

        BigInteger nonce = getNonce();

        for(MultiChooseWinnerDto tx : dto) {
            sendChooseWinerTransaction(tx.getRaffleId(), tx.getRandNum(), nonce);
            nonce = nonce.add(BigInteger.ONE);
        }
    }

    private void sendChooseWinerTransaction(BigInteger raffleId, BigInteger randNum, BigInteger nonce) throws IOException {
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

            if(transactionReceipt.hasError()) {
                Response.Error error = transactionReceipt.getError();
                log.error(error.getMessage());
            }else {
                log.info("choose winner success - txHash :  " + transactionReceipt.getTransactionHash());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private BigInteger getNonce() throws IOException {
        EthGetTransactionCount ethGetTransactionCount = web3jHttpRpc.ethGetTransactionCount(
                PUBLIC_KEY,
                DefaultBlockParameterName.PENDING
        ).send();

        BigInteger nonce = ethGetTransactionCount.getTransactionCount();
        return nonce;
    }

    @Override
    public BigInteger getBlockNumber() {
        try{
            EthBlockNumber blockNumber = web3jHttpRpc.ethBlockNumber().send();
            System.out.println("blockNumber : " + blockNumber.getBlockNumber().longValue());
            return blockNumber.getBlockNumber();
        }catch (IOException e) {
            e.printStackTrace();
            return BigInteger.ZERO;
        }
    }
}
