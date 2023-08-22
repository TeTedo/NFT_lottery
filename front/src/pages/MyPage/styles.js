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
    /* border: 1px solid white; */
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
};
const Left ={
  Container : styled.div`
  
  width: 250px;
  height: 100%;
  display: flex;
  flex-direction: column;
  gap: 25px;
  `,
  Title : styled.div`
  margin-top: 50px;
  color: ${colors.white};
  font-size: 20px;
  `,
  Content : styled.div`
  margin-left: 20px;
  color: ${colors.white};
  font-size: 16px;  
  `

  
}
const Right ={
  Container: styled.div`
  height: 100%;
  flex: 1;
  `,
  Menu: styled.div`
  display: flex;
  gap: 20px;
  padding-bottom: 25px;
  `,
  Item: styled.div`
  font-size: 16px;
  border: 1px solid white;
  padding: 10px 20px;
  border-radius: 30px;
  color: ${colors.white};
  `
}

export { Container, Contents , Left, Right};