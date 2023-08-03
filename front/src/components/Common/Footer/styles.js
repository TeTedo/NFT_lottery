import styled from "styled-components";
import { background, colors } from "../../../pages/globalStyles";

const FooterItem = {
  Container: styled.div`
    padding: 40px 40px 100px;
    border-top: 1px solid ${background.grey};
    display: flex;
    justify-content: space-between;
    color: ${colors.grey};
    background-color: ${background.background1};
  `,
  TeamName: styled.div``,
  Contact: {
    Container: styled.div`
      display: flex;
    `,
    Title: styled.div``,
    Description: styled.div``,
  },
};

export { FooterItem };
