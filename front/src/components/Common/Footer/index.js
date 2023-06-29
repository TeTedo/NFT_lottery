import { FooterItem } from "./styles";

export default function Footer() {
  return (
    <>
      <FooterItem.Container>
        <FooterItem.TeamName>@2023 Team UNDEFINED</FooterItem.TeamName>
        <FooterItem.Contact.Container>
          <FooterItem.Contact.Title>Contact</FooterItem.Contact.Title>
          <FooterItem.Contact.Description>
            undefind1234@gmail.com
          </FooterItem.Contact.Description>
        </FooterItem.Contact.Container>
      </FooterItem.Container>
    </>
  );
}
