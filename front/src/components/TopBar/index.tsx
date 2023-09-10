import { Contents } from "./styles";
import { useNavigation } from "hooks/useNavigation";
import useMetaMask from "hooks/useWallet";
import ShortenAddress from "./ShortenAddress";

export function TopBar() {
  const { goToHome, goToMyPage } = useNavigation();
  const { account, connect }: any = useMetaMask();

  return (
    <Contents.Container>
      <Contents.Content>
        <Contents.Logo onClick={goToHome}>Undefined.</Contents.Logo>
        {account === "" ? (
          <Contents.Login onClick={connect}>
            Connect with MetaMask
          </Contents.Login>
        ) : (
          <Contents.NickName onClick={goToMyPage}>
            Connected Account: <ShortenAddress address={account} />
          </Contents.NickName>
        )}
      </Contents.Content>
    </Contents.Container>
  );
}

export default TopBar;
