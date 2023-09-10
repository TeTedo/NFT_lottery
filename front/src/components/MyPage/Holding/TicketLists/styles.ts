import { colors } from "pages/globalStyles";
import styled from "styled-components";

const TicketContents = {
  Container: styled.div`
    /* border: 1px solid ${colors.grey}; */
    height: 100%;
  `,
  Category: styled.div`
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0 1rem;
    height: 3rem;
    border-bottom: 1px solid ${colors.grey};
    color: ${colors.white};
  `,
  CategoryItem: styled.div`
    text-align: center;
    flex: 1;
    font-size: 20px;
    font-weight: bold;
    padding-right: 20px;
  `,
  Title: styled.div`
    font-size: 30px;
    font-weight: bold;
  `,
};

export { TicketContents };
