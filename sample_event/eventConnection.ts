import { ethers } from "ethers";
import { abi } from "./contract/EventTest.json";
import dotenv from "dotenv";
dotenv.config();

const 웹소켓엔드포인트 = "wss://public-en-baobab.klaytn.net/ws";
const CONTRACT_ADDR = "0x5d73e59868C2EcC79c4A59920931d449346B8680";
const EVENT_NAME = "Test";

async function main(reconnectionCount = 0, lastBlock = 0) {
  const wsProvider = new ethers.providers.WebSocketProvider(웹소켓엔드포인트);
  const contract = new ethers.Contract(CONTRACT_ADDR, abi, wsProvider);

  // reconnector
  if (reconnectionCount > 0) {
    console.log(`${reconnectionCount} 번째 재연결`);
    const currentBlock = await wsProvider.getBlockNumber();
    // 놓친 이벤트 query
    if (lastBlock > 0 && currentBlock > lastBlock) {
      const missedEventLog = await contract.queryFilter(EVENT_NAME, lastBlock + 1, currentBlock);
      console.log(missedEventLog);
    }
  }

  // 콜백함수 파라미터에 이벤트값 다 넣어주는데
  // 자세히 보고싶으면 ...agrs로 보시길
  contract.on(
    EVENT_NAME,
    (_uint, _string, _bool, _address, _bytes, _uintArray, _struct, blockNumber) => {
      console.log({
        _uint: formatter(_uint),
        _string: formatter(_string),
        _bool: formatter(_bool),
        _address: formatter(_address),
        _bytes: formatter(_bytes),
        _uintArray: formatter(_uintArray),
        _struct: formatter(_struct),
      });
      console.log("");
      lastBlock = Number(blockNumber);
    }
  );
  console.log("이벤트 구독중");

  wsProvider._websocket.on("close", () => {
    console.log("연결 끊김");
    reconnectionCount++;
    main(reconnectionCount, lastBlock);
  });
}

main().catch((error) => {
  console.log(error);
  process.exitCode = 1;
});

function formatter<T>(val: T) {
  return { value: val, type: typeof val };
}
