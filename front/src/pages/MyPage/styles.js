import styled from "styled-components";
import { background, colors } from "../globalStyles";

const Container = styled.div`
  width: 100%;
  min-height: calc(100vh);
  background-color: ${background.background1};
  display: flex;
  justify-content: center;
  color: ${colors.white};
  font-weight: 700;
`;
const Contents = {
  Container: styled.div`
    border: 1px solid white;
    margin-top: 130px;
    width: 1400px;
    padding-bottom: 110px;
    display: flex;
  `,
  Menu: styled.div`
    flex: 1;
    width: 300px;
    border: 1px solid white;
  `,
  Menu: styled.div`
    flex: 1;
    width: 300px;
    border: 1px solid white;
  `,
};
export { Container, Contents };
