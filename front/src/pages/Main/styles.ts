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
    margin-top: 130px;
    width: 1400px;  
    padding-bottom: 110px;
  `,
  Title: styled.div`
    font-size: 30px;
    padding-bottom: 30px;
  `,
};

export { Container, Contents };
