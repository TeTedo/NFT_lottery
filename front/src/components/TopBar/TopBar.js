import React from "react";
import { Contents } from "./styles";
import { useNavigation } from "hooks/useNavigation";

export function TopBar() {
  const { goToHome, goToMyPage } = useNavigation();
  return (
    <Contents.Container>
      <Contents.Content>
        <Contents.Logo onClick={goToHome}>Undefined.</Contents.Logo>
        <Contents.TitleContainer>
          <Contents.Title onClick={goToMyPage}>My Page</Contents.Title>
        </Contents.TitleContainer>
        <Contents.Login>Login</Contents.Login>
      </Contents.Content>
    </Contents.Container>
  );
}

export default TopBar;
