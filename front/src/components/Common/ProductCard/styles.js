import styled from "styled-components";
import { background, colors } from "../../../pages/globalStyles";

const Card = {
  Container: styled.div``,
  Img: styled.div`
    width: 250px;
    height: 250px;
    background-color: ${background.grey};
    border-radius: 20px;
  `,
  Contents: styled.div`
    display: flex;
    flex-direction: column;
    text-align: end;
    padding: 10px;
  `,
  Title: styled.div`
    font-size: 20px;
  `,
  Description: styled.div`
    font-size: 16px;
  `,
  Time: styled.div`
    font-size: 16px;
  `,
};

export { Card };
