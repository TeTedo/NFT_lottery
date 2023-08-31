import { colors } from "pages/globalStyles";
import styled from "styled-components";

type CategoryContentProps = {
  name: "NFT" | "Ticket";
  value: string;
};
const HoldingContents = {
  Container: styled.div`
    height: 100%;
    flex: 1;
  `,
  Menu: styled.div`
    display: flex;
    gap: 20px;
    padding-bottom: 25px;
  `,
  Item: styled.div<CategoryContentProps>`
    cursor: pointer;
    font-size: 16px;
    border: 1px solid
      ${(props: any) => {
        if (props.name === props.value) return;
        return colors.dark_grey;
      }};
    padding: 10px 20px;
    border-radius: 30px;
    color: ${(props: any) => {
      if (props.name === props.value) return colors.white;
      return colors.dark_grey;
    }};
  `,
};

export { HoldingContents };
