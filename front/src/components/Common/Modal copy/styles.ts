// import { background, colors } from "pages/globalStyles";
import { Input, TextField } from "@mui/material";
import { DatePicker } from "@mui/x-date-pickers";
import { background, colors } from "pages/globalStyles";
import styled from "styled-components";

const ModalItem = {
  Container: styled.div`
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
    position: fixed;
    z-index: 100;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: rgba(0, 0, 0, 0.5);
  `,
  Box: styled.div`
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 700px;
    height: 500px;
    border: 1px solid ${background.grey};
    background-color: ${background.background1};
    border-radius: 25px;
    color: ${colors.white};
  `,
  Title: styled.div`
    margin-top: 30px;
    font-size: 25px;
  `,
  TextInput: styled(TextField)`
    color: ${colors.white};
    background-color: ${colors.white};
    label.Mui-focused {
      color: ${colors.purple}; // 라벨 색상을 변경합니다.
    }
    .MuiOutlinedInput-root {
      &.Mui-focused fieldset {
        border-color: ${colors.purple}; // 선 색상을 변경합니다.
      }
    }
  `,
  CustomDatePicker: styled(DatePicker)`
    .MuiInputBase-root {
      background-color: ${colors.white}; // 배경색 변경
      /* border: 1px solid ${colors.purple}; // 선 색상 변경 */
      &.Mui-focused fieldset {
        border-color: ${colors.purple}; // 선 색상을 변경합니다.
      }
    }
    &.Mui-selected {
      background-color: ${colors.purple} !important;
    }
  `,
};

export { ModalItem };
