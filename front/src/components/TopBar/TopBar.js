import React, { useState } from "react";
import { Contents } from "./styles";
import { useNavigation } from "hooks/useNavigation";
import useMetaMask from "hooks/useWallet";

export function TopBar() {
  const { goToHome, goToMyPage } = useNavigation();
  const { account, balance, chainId, provider, connect } = useMetaMask();
  console.log("account", account);
  console.log("balance", balance);
  console.log("chainId", chainId);
  console.log("provider", provider);

  return (
    <Contents.Container>
      <Contents.Content>
        <Contents.Logo onClick={goToHome}>Undefined.</Contents.Logo>
        <Contents.TitleContainer>
          <Contents.Title onClick={goToMyPage}>My Page</Contents.Title>
        </Contents.TitleContainer>
        {account === "" ? (
          <button onClick={connect}>Connect with MetaMask</button>
        ) : (
          <p>Connected Account: {account}</p>
        )}
        {/* {!address !== null ? (
          <>
          </>
        ) : (
          <div>연결 계정 : {address}</div>
        )} */}
      </Contents.Content>
    </Contents.Container>
  );
}

export default TopBar;
