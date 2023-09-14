import { background, colors } from "pages/globalStyles";
import styled from "styled-components";

const Card = {
  Container: styled.div``,
  Img: styled.div`
    width: 270px;
    height: 270px;
    background-color: ${background.grey};
    border-radius: 20px;
  `,
  Contents: styled.div`
    display: flex;
    flex-direction: column;
    gap: 10px;
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
  Flex: styled.div`
    /* margin-top: 10px; */
    display: flex;
    justify-content: space-between;
  `,

  Div: styled.div``,
  ButtonDiv: styled.div``,
  Button: styled.button`
    background-color: ${colors.purple};
    border: 0;
    font-size: 16px;
    color: ${colors.white};
    box-shadow: 0;
    /* margin-left: 15px; */
    border-radius: 10px;
    padding: 5px 40px;
    transition: all 0.3s ease-in-out;
    font-weight: 700;
    cursor: pointer;

    &:hover {
      /* border: 2px solid ${colors.white}; */
      /* color: ${colors.white}; */
    }
  `,
};

export { Card };
