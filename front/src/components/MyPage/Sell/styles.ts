import { colors } from "pages/globalStyles";
import styled from "styled-components";

const SellContents = {
  Container: styled.div`
    height: 100%;
    flex: 1;
  `,
  Menu: styled.div`
    display: flex;
    justify-content: space-between;
    /* gap: 20px; */
    padding-bottom: 25px;
  `,
  Item: styled.div`
    cursor: pointer;
    font-size: 16px;
    border: 1px solid ${colors.white};
    padding: 10px 20px;
    border-radius: 30px;
    color: ${colors.white};
  `,
  Button: styled.button`
    background-color: ${colors.purple};
    border: 0;
    font-size: 16px;
    color: ${colors.white};
    box-shadow: 0;
    /* margin-left: 15px; */
    border-radius: 10px;
    padding: 10px 25px;
    transition: all 0.3s ease-in-out;
    font-weight: 700;
    cursor: pointer;
    margin-right: 60px;

    &:hover {
      /* border: 2px solid ${colors.white}; */
      /* color: ${colors.white}; */
    }
  `,
};

export { SellContents };
