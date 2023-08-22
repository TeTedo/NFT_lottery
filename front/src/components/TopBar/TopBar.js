import React, { useState } from "react";
import { Contents } from "./styles";
import { useNavigation } from "hooks/useNavigation";
// import useWeb3 from "hooks/useWeb3";
import useConnectMetaMask from "hooks/useWeb3";

export function TopBar() {
  const { goToHome, goToMyPage } = useNavigation();
  const { connect, accounts, isConnected } = useConnectMetaMask();
  console.log(accounts);
  console.log(isConnected);
  return (
    <Contents.Container>
      <Contents.Content>
        <Contents.Logo onClick={goToHome}>Undefined.</Contents.Logo>
        <Contents.TitleContainer>
          <Contents.Title onClick={goToMyPage}>My Page</Contents.Title>
        </Contents.TitleContainer>
        {!isConnected ? (
          <>
            <button onClick={() => connect("metamask")}>
              Connect with MetaMask
            </button>
          </>
        ) : (
          <div>연결 계정 :  {accounts[0]}</div>
        )}
      </Contents.Content>
    </Contents.Container>
  );
}

export default TopBar;
