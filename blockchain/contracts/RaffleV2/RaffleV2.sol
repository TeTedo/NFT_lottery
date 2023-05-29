// SPDX-License-Identifier: MIT

pragma solidity ^0.8.0;

import "./core/RaffleSale.sol";
import "./core/RaffleClaim.sol";

// 1. registerRaffle을 통해 레플등록 -> _raffles에 RaffleInfo 등록됨
// 2. buyTickets를 통해 티켓 구매(레플 참여)가능 -> batch로 구매
// 3. 경매시간 마감 혹은 티켓이 다 팔린 경우 chooseWinner실행 -> 이건 일단 백서버에서 owner 계정으로 실행시킨다는 전제로 구성
// chooseWinner로직에서 랜덤으로 winner를 선발한뒤 _raffles에서 해당 RaffleInfo 삭제(어차피 DB에 저장하고 있을꺼고 event에서 제공되는 블록넘버로 언제던지 블록체인 히스토리 열람 가능)

// * 전체경매조회 및 상장된 nft 리스트를 불러오는 기능은 없음 (현재 mapping으로 유지중)
// * 해당 기능을 위해선 추가적으로 배열을 만들어줘야하는데 배열 길이가 길어지면 (특히 삭제할때) 어느정도 순회가 필수적이라서
// * 가스비적인 측면에서 손해임
// * 해당 리스트들은 오프체인(서버 DB)에서 따로 유지시켜주는 방향이 좋을듯

contract RaffleV2 is RaffleSale, RaffleClaim {
    constructor() {
        _disableInitializers();
    }

    function initialize(
        uint256 maxTicketAmount_,
        uint256 minTicketPrice_,
        uint8 raffleFeePercentage_
    ) external initializer {
        __RaffleInfo_init(
            maxTicketAmount_,
            minTicketPrice_,
            raffleFeePercentage_
        );
        __Ownable_init();
        __ReentrancyGuard_init();
    }
}
