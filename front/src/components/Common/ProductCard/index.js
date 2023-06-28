import { Card } from "./styles";

export default function ProductCard() {
  return (
    <>
      <Card.Container>
        <Card.Img />
        <Card.Contents>
          <Card.Title>product description</Card.Title>
          <Card.Description>price</Card.Description>
          <Card.Time>left time</Card.Time>
        </Card.Contents>
      </Card.Container>
    </>
  );
}
